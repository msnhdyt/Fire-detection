# reference: https://github.com/Rocksus/object-detection-hls/blob/master/internal/detector.py
import tensorflow as tf
import sys
import numpy as np
from pyfcm import FCMNotification
import time
import requests
from os.path import dirname, abspath

class Detector:
    def __init__(self, input_url):
        self.url = input_url
        self.model = tf.keras.models.load_model(dirname(dirname(abspath(__file__))) + "/Model/Fire_Detection_v4.h5")
        self._running = True
        self.fire_detected = False
        self.detected_img = None
    
    def run(self):
        cap = cv2.VideoCapture(self.url)
        if cap.isOpened() == False:
            print(f"unable to open url '{self.url}'")
            sys.exit(-1)

        while self._running:
            ret, frame = cap.read()
            if ret:
                frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
                pass
            else:
                print("stream end")
                break
            img = cv2.resize(frame, (224, 224)).astype('float32')
            img = tf.expand_dims(img, axis=0)
            img = tf.keras.applications.mobilenet_v2.preprocess_input(img)
            prediction = self.model.predict(img)

            if prediction[0][0] >0:
              self.fire_detected = False
            else:
              self.fire_detected = True
            result = np.asarray(frame)
            result = cv2.cvtColor(np.array(frame), cv2.COLOR_RGB2BGR)
            self.detected_img = result
            
        cap.release()
    
    def get_status(self):
        return (self.fire_detected, self.detected_img)

    def terminate(self):
        self._running = False

    def send_notif(self):

        device = requests.get("http://bumdesgemahripahsawotratap.com/token.php")
        registration_id = device.json()['data']
        api_key = "AAAAkzj3f1s:APA91bFrCEADNVrnxfhY-4PsMKoemf8NUCqP-sF7fJQPkHJJ4ur4p_3OZnIzN6mHUJhAF6JYUFher8rt8o89dcwBdOcTY4smroYHhGjvN7v3nDrCblZ4moCRbpzsNdReaR9RJ0zhVwT4"
        push_service = FCMNotification(api_key= api_key)
        message_title = "Fire Detected"
        message_body = "Hey, we have detected fire. please check the app!"

        time.sleep(1)
        fire, img = self.get_status()
        notif_sent = False

        while True:
          fire, img = self.get_status()

          if fire and not notif_sent :
            result = push_service.notify_multiple_devices(registration_ids=registration_id, message_title=message_title, message_body=message_body)
            print("notification has been sent")
            notif_sent = True
          elif not fire :
            notif_sent = False
          time.sleep(1)
