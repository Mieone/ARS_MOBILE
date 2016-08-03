from flask import Flask
from flask import jsonify, request
import optparse
import traceback
import MySQLdb
from ConfigParser import SafeConfigParser
_cfg = SafeConfigParser()
_cfg.read('db_names.cfg')
app = Flask(__name__)

@app.route('/albl-brand')
def albl():
    query = 'select brand , channel from BrandChannel';
    brand = 'albl'
    host = _cfg.get(brand, 'host')
    db_user = _cfg.get(brand, 'db_user')
    db_passwd = _cfg.get(brand, 'db_passwd')
    reports_db = _cfg.get(brand, 'ars_db')
    conn = MySQLdb.connect(host=host, user=db_user, passwd=db_passwd, db=reports_db)
    cursor = conn.cursor()
    cursor.execute(query)
    details_list = []

    rows = cursor.fetchall()
    for row in rows:
        brand = row[0]
        channel =row[-1]
        _dict = {'brand': brand, 'channel':channel}
        details_list.append(_dict)
    details = {'result': details_list}
    return jsonify(**details)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=9305, debug=True)


