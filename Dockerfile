FROM ubuntu:20.04

ENV DEBIAN_FRONTEND noninteractive
ENV DEBIAN_FRONTEND teletype
ENV _JAVA_AWT_WM_NONREPARENTING=1

RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install curl apt-utils -y
RUN apt-get install software-properties-common -y
RUN apt-get install default-jre -y


RUN curl "https://r4---sn-jvhj5nu-nh4e.gvt1.com/edgedl/android/studio/ide-zips/2022.1.1.20/android-studio-2022.1.1.20-linux.tar.gz?cms_redirect=yes&amp;mh=bG&amp;mip=2601:203:4003:6200::927d&amp;mm=28&amp;mn=sn-jvhj5nu-nh4e&amp;ms=nvh&amp;mt=1676493622&amp;mv=m&amp;mvi=4&amp;pcm2cms=yes&amp;pl=39&amp;rmhost=r6---sn-jvhj5nu-nh4e.gvt1.com&amp;shardbypass=sd" --output /root/android_studio.tar.gz

RUN mkdir /root/android
RUN tar -xvf /root/android_studio.tar.gz -C /root/


WORKDIR /root/android-studio
