import json
import datetime
import pandas as pd
from flask import Flask, request
from flask_cors import CORS, cross_origin
from flask_restful import Resource, Api
from flask_jsonpify import *
from flask.json import JSONEncoder
from datetime import date, datetime

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

@app.route("/")
def hello():
    return jsonify({ 'message' : 'Hello World!!!' })

class HelloName(Resource):
    def get(self):
        return jsonify({ 'Message' : 'Hello ' + str(request.args['name']) + '!'})

api.add_resource(HelloName,'/hello')

if __name__ == '__main__':
    app.run( debug = True, host = '0.0.0.0' )