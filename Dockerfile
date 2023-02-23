FROM ubuntu:20.04

ENV DEBIAN_FRONTEND noninteractive
ENV DEBIAN_FRONTEND teletype
ENV _JAVA_AWT_WM_NONREPARENTING=1

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install curl apt-utils -y
RUN apt-get install software-properties-common -y


RUN curl "https://r4---sn-jvhj5nu-nh4e.gvt1.com/edgedl/android/studio/ide-zips/2022.1.1.20/android-studio-2022.1.1.20-linux.tar.gz?cms_redirect=yes&amp;mh=bG&amp;mip=2601:203:4003:6200::927d&amp;mm=28&amp;mn=sn-jvhj5nu-nh4e&amp;ms=nvh&amp;mt=1676493622&amp;mv=m&amp;mvi=4&amp;pcm2cms=yes&amp;pl=39&amp;rmhost=r6---sn-jvhj5nu-nh4e.gvt1.com&amp;shardbypass=sd" --output /root/android_studio.tar.gz

RUN mkdir /root/android
RUN tar -xvf /root/android_studio.tar.gz -C /root/
RUN dpkg --add-architecture i386
RUN apt-get update && apt-get install -y \
        build-essential
RUN apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 libbz2-1.0:i386 \
        libxrender1 libxtst6 libxi6 libfreetype6 libxft2 xz-utils vim\
        qemu qemu-kvm libvirt-daemon-system libvirt-clients  bridge-utils libnotify4 libglu1 libqt5widgets5 openjdk-8-jdk xvfb

WORKDIR /root/android-studio
