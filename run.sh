#!/bin/sh

xhost local:root
XAUTH=/tmp/.docker.XAUTH


docker exec -i and /root/android-studio/bin/studio.sh &
