import os
import xml.etree.ElementTree as ET
import cv2
import numpy as np


def load_pascal_annotation(filepath):
    """
    Load image and bounding boxes info from XML file in the PASCAL VOC
    format.
    """

    tree = ET.parse(filepath)
    objs = tree.findall('object')

    for obj in objs:
        label = obj.find('name').text
        # Make pixel indexes 0-based

        if label=="northgated":
            print(label)
            obj.find("name").text = "northgate"
        # if obj.find('name').text.lower().strip()=='zhongloud':
        # print(index)
            tree.write(filepath)



foldepath = "/home/wlk/桌面/DIYdataV4/pics"
for f in os.listdir(foldepath):
    end =str(f)[-3:]
    if end=="xml":
        load_pascal_annotation(os.path.join(foldepath,f))

