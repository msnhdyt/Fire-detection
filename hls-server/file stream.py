import os
import ffmpeg_streaming
from ffmpeg_streaming import Formats

path = os.path.abspath(os.path.dirname(__file__))

video = ffmpeg_streaming.input(path + "\cctv.mp4")

hls = video.hls(Formats.h264())
hls.auto_generate_representations()
hls.output(path + '/streams/cctv.m3u8')