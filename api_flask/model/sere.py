from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, Float, Date
from geoalchemy2 import Geometry

Base = declarative_base()

class SERE(Base):
    __tablename__ = 'sere_images'
    id = Column(Integer, primary_key = True)
    latitude = Column(Float)
    longitude = Column(Float)
    font = Column(String)
    band = Column(String)
    image_date = Column(Date)
    image_url = Column(String)