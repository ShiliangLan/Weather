#-*- coding: UTF-8 -*- 
from flask import Flask, request
import sys, urllib2

app = Flask(__name__)

@app.route('/')
def hello_world():  
    info = request.args.get('info')
    url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+info+"&sensor=false"
	header= {'Accept-Language':'zh-CN,zh;q=0.8'}
	req = urllib2.Request(url,headers = header)
	r = urllib2.urlopen(req)
	json = r.read()
	return json

if __name__ == '__main__':  
    app.run(host='0.0.0.0')