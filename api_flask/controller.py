import requests
import pandas as pd
from datetime import date, datetime
from flask import Flask, request, abort
from flask_cors import CORS, cross_origin
from flask_restful import Resource, Api
from flask_jsonpify import *
from flask.json import JSONEncoder

from .model.brasil import Brasil
from .model.database import DBEngine

class CustomJSONEncoder(JSONEncoder):
    def default(self, obj):
        try:
            if isinstance(obj, date):
                return obj.isoformat()
            iterable = iter(obj)
        except TypeError:
            pass
        else:
            return list(iterable)
        return JSONEncoder.default(self, obj)

app = Flask(__name__)
app.json_encoder = CustomJSONEncoder
api = Api(app)
CORS(app)

def length(obj):
    aux, i = None, 0
    while True:
        try: aux, i = obj[i], i + 1
        except: break
    return i

@app.route("/hello", methods=['GET', 'POST'])
def helloName():
    if request.method == 'GET':
        return { "Message" : "Hello " + str(request.args['name']) + "!"}

@app.route("/api/teste_db", methods=['GET'])
def teste_db():
    if request.method == 'GET':
        engine = DBEngine.get_engine()
        session = DBEngine.open_session(instanciar=True)
        if engine:
            return {
                "Message" : session.query(Brasil).filter_by(geocodigo = '3549904').first().nome1
            }
        else:
            abort(404, description = "error database connection refused!")

@app.route("/api/municipios", methods=['GET'])
def getMunicipios():
    if request.method == 'GET':
        engine = DBEngine.get_engine()
        session = DBEngine.open_session(instanciar=True)
        try:
            if engine:
                municipios = session.query(Brasil).filter_by(uf = str(request.args['uf']).upper())
                response = []
                for i in range(length(municipios)):
                    response.append(
                        {
                            "nome_municipio" : municipios[i].nome1,
                            "uf" : municipios[i].uf,
                            "regiao" : municipios[i].regiao,
                            "geocodigo" : municipios[i].geocodigo,
                            "latitude" : municipios[i].latitude,
                            "longitude" : municipios[i].longitude
                        }
                    )
                return {
                    "municipios" : response
                }
            else:
                return abort(500, description = { "info" : "error database connection refused!"})
        except:
            return abort(404)
    else:
        abort(404)

@app.route("/api/images", methods=['GET'])
def getImages():
    if request.method == 'GET':
        try:
            return { "response" : "return base urls for download the images" }
        except:
            return abort(500, description = { "info" : "error database connection refused!"})
    else:
        abort(404)