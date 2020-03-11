from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from . import pg as creds

class DBEngine(object):

    ECHO = True
    ENGINE = None

    def __init__(self):
        pass

    @staticmethod
    def get_engine():
        if DBEngine.ENGINE is None:
            DBEngine.ENGINE = create_engine(
                'postgresql://{user}:{password}@{host}:{port}/{database}'.format(
                    user = creds.pgUser,
                    password = creds.pgPassWord,
                    host = creds.pgHost,
                    port = creds.pgPort,
                    database = creds.pgDataBase
                ),
                echo=DBEngine.ECHO
            )
        return DBEngine.ENGINE

    @staticmethod
    def open_session(instanciar=False):
        session = sessionmaker(bind=DBEngine.get_engine())
        # session.add(brasil)
        # session.commmit()
        # session.query(brasil)
        # session.query(brasil).filter_by(name='SP')  # .first()
        return session() if instanciar else session
