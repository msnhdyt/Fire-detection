# Fire-detection
Bangkit Capstone Project (B21-CAP0484)

This project implemented machine learning to detect fire from HLS video stream and then will send notifications to the android app. We used dataset from [kaggle](https://www.kaggle.com/tharakan684/urecamain)

## Requierements
To run this project, ensure you have:
- Video stream (you can put the video stream to `hls-server/streams`)
- Installed requirements.txt

## Installation
- run HLS server
`node hls-server/index.js`
- run main server
`python server/main.py`
- install the android app
- to get notifications, please click `turn on` first in the app. You will be notified if fire was detected. (or you can request to `localhost/turn_on`)
- then you can click `get image` to get the frame of the video. (or you can request to `localhost/get_status`)

## Flowchart
![flowchart capstone](https://user-images.githubusercontent.com/56325833/120769525-6c953600-c547-11eb-9683-1a6f201ee94b.jpeg)

## Code information
    + hls-server
      + streams                   # video streams folder
      + cctv.mp4
      + index.js                  # code to run hls server
      + file stream.py            # code to generate video stream
      + requirements.txt
    + Model
      + fire_detection_v2.h5
      + Fire_Detection_v2.ipynb
    + server
      + main.py                   # code to run main server
      + requirements.txt
      + detector.py
