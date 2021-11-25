# from keras.models import load_model, Model
# from keras import backend as K
# from keras.preprocessing import image
# from keras.preprocessing.image import load_img

import tensorflow as tf
from tensorflow import keras #needed
from Processes import preprocessing, feature_extraction #needed
import cv2 as cv #needed
import numpy as np #needed
from PIL import Image #needed
import io #needed
import base64 #needed

def new_signature(image1, image2, image3, filename):

    data = image1
    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv.imdecode(np_data, cv.IMREAD_GRAYSCALE)
    pil_im = Image.fromarray(img)
    processed = preprocessing(pil_im)

    pil_im = Image.fromarray(processed)
    buff = io.BytesIO()
    pil_im.save(buff, format="PNG")
    img_str = base64.b64encode(buff.getvalue())
    return ""+str(img_str,'utf-8')


    
    # for i in range (len(image_array)):
    #     data = image_array[i]
    #     decoded_data = base64.b64decode(data)
    #     np_data = np.fromstring(decoded_data, np.uint8)
    #     img = cv.imdecode(np_data, cv.IMREAD_UNCHANGED)
    #     processed = preprocessing(img)

    # cv.imshow("image", processed)
    # cv.waitKey(0)
    # base_model = load_model('./Model/cnn_model_new_preprocessing.h5')
    # img = feature_extraction(processed, base_model)
    # return img
    # return processed

def verify_signature():
    return 0

# image = cv.imread('C:/Users/inazu/Desktop/OpenCv/Signatures_Dataset/English/001/org/original_1_1.png', cv.IMREAD_UNCHANGED)
# pil_im = Image.fromarray(image)

# img = new_signature(pil_im)
# print(img)