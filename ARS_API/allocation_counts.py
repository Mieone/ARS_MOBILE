from flask import Flask
from flask import jsonify, request
import optparse
import traceback
import MySQLdb
from ConfigParser import SafeConfigParser
_cfg = SafeConfigParser()
_cfg.read('db_names.cfg')

fail_query = 'select customer, reason from InboundCheck where allocate_flag= "N" and active_flag="Y" and brand="%s" and channel="%s"'
success_query = 'select customer, reason from InboundCheck  where allocate_flag="Y" and brand="%s" and channel="%s"'
m_query = 'select customer, cust_name from  PlanoValidation where classification ="%s" and brand="%s" and channel="%s"'
userid_query = 'select id from auth_user where username="%s"'
userbrandchannel_query = 'select brand_channel_id from UserBrandChannel where user_id = "%s"'
brandchannel_query = 'select brand, channel from BrandChannel where id = "%s"'

app = Flask(__name__)

def_brands = ['bb', 'ultd']

def get_details(request):
    count_details = []
    _type = request.form['type']
    brand = request.form['brand'].lower()
    if brand == 'unlimited':
        brand = 'ultd'
    if brand not in def_brands:
        brand= 'albl'
    channel = request.form['channel'].upper()
    host = _cfg.get(brand, 'host')
    db_user = _cfg.get(brand, 'db_user')
    db_passwd = _cfg.get(brand, 'db_passwd')
    reports_db = _cfg.get(brand, 'ars_db')
    conn = MySQLdb.connect(host=host, user=db_user, passwd=db_passwd, db=reports_db) 
    cursor = conn.cursor()
    master_details,count_details = [], {'result': []}
    brand = request.form['brand'].upper()
    if 'ARS' in request.url:
        if _type == 'error':
            query = fail_query %(brand, channel)
        if _type == 'success':
            query = success_query %(brand, channel)
    else:
        query = m_query %(_type.upper(), brand, channel)
    cursor.execute(query)
    master_rows = cursor.fetchall()
    return master_rows, cursor


@app.route('/ARS',  methods = ['POST', 'GET'])
def ars_type():
    ars_details,count_details = [], {'result': []}
    rows, cursor = get_details(request)
    for row in rows:
        reasons = []
        customer = row[0]
        try:
            cust_name = get_name(customer, cursor, request)
        except:
            cust_name = ''
        reasons.append(row[-1])
        details = {'Customer': customer, 'Reason':reasons, 'Cust_Name': cust_name}
        ars_details.append(details)
    ars_count = {'results': ars_details}
    cursor.close()
    return jsonify(**ars_count)

@app.route('/Master', methods = ['POST', 'GET'])
def master_type():
    master_details,count_details = [], {'result': []}
    _type = request.form['type']
    master_rows, cursor= get_details(request)
    for row in master_rows:
        customer = row[0]
        errors_list = get_validation_errors(_type, customer, cursor, request)
        reason = row[-1]
        master_detail = {'Customer': customer, 'Cust_Name':reason, 'Reason':errors_list}
        master_details.append(master_detail)
    master_count = {'results': master_details}
    cursor.close()
    return jsonify(**master_count)

def get_validation_errors(_type, customer, cursor, request):
    brand = request.form['brand'].upper()
    channel = request.form['channel'].upper()
    mas_error_query = 'select validation_name from PlanoValidationReport where customer = "%s" and validation_type = "%s" and brand="%s" and channel="%s"' %(customer, _type, brand, channel)
    cursor.execute(mas_error_query)
    validations = []
    rows = cursor.fetchall()
    for row in rows:
        validations.append(row[0])
    return validations

def get_name(customer, cursor, request):
    channel = request.form['channel'].upper()
    cust_query = 'select cust_name from CustomerMaster where cust_code = "%s" and channel="%s"' %(customer, channel)
    cursor.execute(cust_query)
    rows = cursor.fetchall()
    return rows[0][0]

@app.route('/albl-brand' , methods = ['POST', 'GET'])
def albl():
    details_list = []
    username = request.form['username']
    brand = 'albl'
    host = _cfg.get(brand, 'host')
    db_user = _cfg.get(brand, 'db_user')
    db_passwd = _cfg.get(brand, 'db_passwd')
    reports_db = _cfg.get(brand, 'ars_db')
    conn = MySQLdb.connect(host=host, user=db_user, passwd=db_passwd, db=reports_db)
    cursor = conn.cursor()
    cursor.execute(userid_query %username)
    user_id = cursor.fetchall()[0][0]
    cursor.execute(userbrandchannel_query % str(user_id))
    brandchannel_ids = cursor.fetchall()
    for brandchannel_id in brandchannel_ids:
        cursor.execute(brandchannel_query % str(brandchannel_id[0]))
        rows = cursor.fetchall()
        for row in rows:
            brand = row[0]
            channel =row[-1]
            _dict = {'brand': brand, 'channel':channel}
            details_list.append(_dict)
    details = {'result': details_list}
    return jsonify(**details)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=9301, debug=True)

