
import pandas as pd


from datetime import date, datetime

from flask import Flask, request
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

@app.route("/hello", methods=['GET', 'POST'])
def helloName():
    if request.method == 'GET':
        return { "Message" : "Hello " + str(request.args['name']) + "!"}


@app.route("/teste_db", methods=['GET'])
def teste_db():
    if request.method == 'GET':
        engine = DBEngine.get_engine()
        session = DBEngine.open_session(instanciar=True)
        if engine:
            return {
                "Message" : session.query(Brasil).filter_by(geocodigo = '3549904').first().nome1
            }

