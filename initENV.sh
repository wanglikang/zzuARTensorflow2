#!/usr/bin/env bash
apt-get update
apt-get upgrade
apt-get install screen
apt-get install python3-dev
apt-get install python3-pip
rm /usr/bin/python
ln -s /usr/bin/python3 /usr/bin/python


pip3 install tensorflow-gpu==1.4.0