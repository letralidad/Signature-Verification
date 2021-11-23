from Processes import preprocessing, feature_extraction
import cv2 as cv
import numpy as np
import tensorflow as tf
from PIL import Image
from keras.models import load_model, Model
from keras import backend as K
from keras.preprocessing import image
from keras.preprocessing.image import load_img


def new_signature(image):
    processed = preprocessing(image)
    cv.imshow("image", processed)
    cv.waitKey(0)
    base_model = load_model('./Model/cnn_model_new_preprocessing.h5')
    img = feature_extraction(processed, base_model)
    return img

def verify_signature():
    return 0

image = cv.imread('C:/Users/inazu/Desktop/OpenCv/Signatures_Dataset/English/001/org/original_1_1.png', cv.IMREAD_UNCHANGED)
pil_im = Image.fromarray(image)

img = new_signature(pil_im)
print(img)