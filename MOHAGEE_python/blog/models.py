from django.db import models

# Create your models here.
class resturant(models.Model):
    name = models.CharField(max_length=50)
    type = models.CharField(max_length=50)
    location = models.TextField()
    time = models.TextField()
    boys_like = models.FloatField()
    girls_like = models.FloatField()
    mood = models.CharField(max_length=50)
