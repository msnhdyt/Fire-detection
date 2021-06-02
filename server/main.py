import cv2
import threading
import base64
import time
from flask import Flask, request, json
#from flask_ngrok import run_with_ngrok
from PIL import Image
from io import BytesIO
from detector import Detector

app = Flask(__name__)
#run_with_ngrok(app)

cctvs_connected = {}

@app.route("/")
def hello():
  return "Hello!"

@app.route("/turn_on")
def turn_on():
  url = "{url}/streams/cctv_360p.m3u8"
  detector = Detector(url)
  cctvs_connected['id1']=detector
  run = threading.Thread(
      target=cctvs_connected['id1'].run, daemon=True
  )
  run.start()

  notif = threading.Thread(target=cctvs_connected['id1'].send_notif)
  notif.start()

  return json.dumps({'status':"CCTV connected"})

@app.route("/get_status")
def get_status():
  try:
    fire, img = cctvs_connected['id1'].get_status()
    while img is None:
      time.sleep(1)
      fire, img = cctvs_connected['id1'].get_status()
    pil_img = Image.fromarray(img)
    buff = BytesIO()
    pil_img.save(buff, format="JPEG")
    new_image_string = base64.b64encode(buff.getvalue()).decode("utf-8")

    return json.dumps({'img': new_image_string})
  except:
    return "please click turn on first"

if __name__ == '__main__':
  app.run()