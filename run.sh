#!/bin/sh

xhost local:root
XAUTH=/tmp/.docker.XAUTH


sudo docker start and
sudo docker exec -i and /root/android-studio/bin/studio.sh &
