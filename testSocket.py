import json
import socket
import os
import sys
import six

import yolo.myconfig as cfg

#a b c d
def int2byte4(number):
    bytes=bytearray(4)
    bytes[0] = number>>24&0xff
    bytes[1] = number>>16&0xff
    bytes[2] = number>>8&0xff
    bytes[3] = number&0xff
    print("{}:{}:{}:{}".format(bytes[0],bytes[1],bytes[2],bytes[3]))
    return bytes
    pass

def sendLenAndData(msocket,data):
    datalen = int2byte4(len(str(data)))
    msocket.send(datalen)
    msocket.send(str(data).encode("utf-8"))

def test():
    zipfilename = "testfilename.txt"
    step = 121


    sendData = {"state":"prepared",
                    "filename":str(zipfilename),
                    "filepath":os.path.join(os.getcwd(),cfg.OUTPUT_DIR, cfg.DATA_VERSION,zipfilename),
                    "step":step,
                    }

    msocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    msocket.connect(('localhost', cfg.listenerport))
    #msocket.send("this is a message".encode("utf-8"))
    sendLenAndData(msocket,sendData)
    sendLenAndData(msocket, sendData)
    sendLenAndData(msocket, sendData)
    sendLenAndData(msocket,"end")

    msocket.close()


if __name__=='__main__':
    test()
'''
    bytee = int2byte4(159)
    for b in bytee:
        print(b)


'''