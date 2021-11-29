# from keras.models import load_model, Model
# from keras import backend as K
# from keras.preprocessing import image
# from keras.preprocessing.image import load_img

import tensorflow as tf
from tensorflow import keras #needed
from commands.Processes import imageProcessing, sqlitecommmands
# from commands.Processes.imageProcessing import preprocessing, feature_extraction #needed
import cv2 as cv #needed
import numpy as np #needed
from PIL import Image #needed
import io #needed
import base64 #needed

# from com.chaquo.python import Python

import sqlite3
import os.path

# The classes inside Processes.py
imgproc = imageProcessing()
sql = sqlitecommmands()

# Directory of Signature database
package_dir = os.path.abspath(os.path.dirname(__file__))
db_dir = os.path.join(package_dir, 'SignatureDatabase.db')

# Connection to database
connection = sqlite3.connect(db_dir)
connection.row_factory = sqlite3.Row
cursor = connection.cursor()

# name of the table in SignatureDatabase.db
IMG_TABLE = "ImageTable"

# def getData():
#     cursor.execute(
#             f"SELECT Image FROM {IMG_TABLE} WHERE Name = 'bitmap1'"
#         )
#     bitmap_1 = cursor.fetchone()
#     # print(bitmap_1)
#     bitmap_2 
#     bitmap_3
    
#     cursor.close()
#     return bitmap_1

# def saving():
#     files_dir = str(Python.getPlatform().getApplication().getFilesDir())
#     return files_dir


def new_signature(image1, name):# , image2, image3, filename
    data = image1
    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv.imdecode(np_data, cv.IMREAD_GRAYSCALE)
    pil_im = Image.fromarray(img)
    processed = imgproc.preprocessing(pil_im)
    feature = imgproc.preprocessing(image_file)

    # insertQuery(processed, name)

    # pil_im = Image.fromarray(processed)
    # buff = io.BytesIO()
    # pil_im.save(buff, format="PNG")
    # img_str = base64.b64encode(buff.getvalue())
    # return ""+str(img_str,'utf-8')


    
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
def insertQuery(img_arr, name):
    insert = f"""
        INSERT INTO {IMG_TABLE} VALUES ('{name}', {img_arr})
    """
    cursor.execute(insert)

def deleteQuery():
    delete = f"""
        DELETE FROM {IMG_TABLE}
    """
    cursor.execute(delete)

def closeDB():
    cursor.close()
    connection.close()

def verify_signature():
    return 0



# image = cv.imread('C:/Users/inazu/Desktop/OpenCv/Signatures_Dataset/English/001/org/original_1_1.png', cv.IMREAD_UNCHANGED)
# pil_im = Image.fromarray(image)

# img = new_signature(pil_im)
# print(img)
# getData()

