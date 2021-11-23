from PIL import Image
from PIL.ImageOps import invert
import numpy as np

from keras.models import load_model, Model
from keras import backend as K
from keras.preprocessing import image


def preprocessing(image_file):
    image_file = image_file.convert('L').resize([220, 155])
    image_file = invert(image_file)
    image_array = np.array(image_file)
    for i in range(image_array.shape[0]):
        for j in range(image_array.shape[1]):
            if image_array[i][j] <= 50:
                image_array[i][j] = 0
            else:
                image_array[i][j] = 255
    return image_array

def feature_extraction(img, model):
    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    flatten_features = model.predict(x)
    print(flatten_features)

    return flatten_features
    

# def SVM():