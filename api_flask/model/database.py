
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker


class DBEngine(object):

    ECHO = True
    ENGINE = None

    def __init__(self):
        pass

    @staticmethod
    def get_engine():
        if DBEngine.ENGINE is None:
            DBEngine.ENGINE = create_engine('postgresql://postgres:mysecretpassword@localhost:5433/template_postgis', echo=DBEngine.ECHO)
        return DBEngine.ENGINE

    @staticmethod
    def open_session(instanciar=False):
        session = sessionmaker(bind=DBEngine.get_engine())
        # session.add(brasil)
        # session.commmit()
        # session.query(brasil)
        # session.query(brasil).filter_by(name='SP')  # .first()
        return session() if instanciar else session
