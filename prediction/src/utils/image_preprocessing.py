import numpy as np
from PIL import Image
import math

target_dims = [183, 256]


def rezise_nparray_image(npimage):
    img = Image.fromarray(npimage)
    dim_to_max = 1 * (img.size[1] > img.size[0])
    ratios = [img.size[1] / img.size[0], img.size[0] / img.size[1]]
    # Image is shorter in both dims than the target
    if img.size[0] < target_dims[0] and img.size[1] < target_dims[1]:
        new_sizes = [0, 0]
        new_sizes[dim_to_max] = target_dims[dim_to_max]
        new_sizes[(dim_to_max + 1) % 2] = math.floor(ratios[dim_to_max] * target_dims[dim_to_max])
        img = img.resize(tuple(new_sizes), Image.ANTIALIAS)
    # The image has at least one dimension bigger than the target
    else:
        img.thumbnail(tuple(target_dims), Image.ANTIALIAS)
    # Create new blank image for resized image insertion
    new_img = Image.new("RGB", (target_dims[0], target_dims[1]), (255, 255, 255))
    new_img.paste(img, ((target_dims[0] - img.size[0]) // 2, (target_dims[1] - img.size[1]) // 2))
    return new_img


def preprocess_image(image):
    return np.array(rezise_nparray_image(image))
