from google.appengine.ext import db
minuta_parecer = db.ListProperty(bool, indexed=True, default=[])
