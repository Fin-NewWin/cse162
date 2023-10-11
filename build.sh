#!/bin/sh

sudo docker rm -f and
sudo docker build -t android_docker .

sudo docker run -idt \
    --name=and\
    --env="DISPLAY=$DISPLAY"\
    --env PULSE_SERVER=unix:/tmp/pulseaudio.socket \
    --volume /tmp:/tmp \
    --env="QT_X11_NO_MITSHM=1"\
    --volume="/tmp/.X11-unix:/tmp/.X11-unix:rw"\
    --env="XAUTHORITY=$XAUTH"\
    --net=host \
    --privileged \
    android_docker \
    bash
