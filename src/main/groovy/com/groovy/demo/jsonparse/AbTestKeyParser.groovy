package com.groovy.demo.jsonparse

import groovy.json.JsonSlurper

/**
 * Html: http://abtest-controlcenter.coupang.net/tests?pageNumber=1&teams=1004&pageSize=20
 * Json: http://abtest-controlcenter.coupang.net/ui/api/v1/tests?pageNumber=1&teams=1004&pageSize=20
 * Curl: curl -u "jqiao:jqiao@COUPANG4" "http://abtest-controlcenter.coupang.net/ui/api/v1/tests?pageNumber=1&teams=1004&pageSize=20"
 *
 * Created by qiaorongrong on 3/1/16.
 */
final String SPLITER = '\t'
final String STANDBY = '--'

File file = new File('json1.json')
def jsonTxt = file.getText('UTF-8')

def jsonSlurper = JsonSlurper.newInstance()
def json = jsonSlurper.parseText(jsonTxt)
//println('pageNumber : ' + json.pageNumber)

json.entities.each {
    StringBuilder row = new StringBuilder()
    row.append(it.id)
    row.append(SPLITER).append(it.name)
    row.append(SPLITER).append(it.status)
    row.append(SPLITER).append(it.winner)

    //platforms
    def platforms = it.platforms
    def android = platforms.android
    if (android.enabled) {
        row.append(SPLITER).append(android.operator).append(android.versions)
    } else {
        row.append(SPLITER).append(STANDBY)
    }
    def ios = platforms.ios
    if (ios.enabled) {
        row.append(SPLITER).append(ios.operator).append(ios.versions)
    } else {
        row.append(SPLITER).append(STANDBY)
    }
    def mobileWeb = platforms.mobileWeb
    if (mobileWeb.enabled) {
        row.append(SPLITER).append(mobileWeb.operator).append(mobileWeb.versions)
    } else {
        row.append(SPLITER).append(STANDBY)
    }
    def pcWeb = platforms.pcWeb
    if (pcWeb.enabled) {
        row.append(SPLITER).append(pcWeb.operator).append(pcWeb.versions)
    } else {
        row.append(SPLITER).append(STANDBY)
    }

    //timestamps
    row.append(SPLITER).append(truncateDate(it.timestamps.created))
    row.append(SPLITER).append(truncateDate(it.timestamps.started))
    row.append(SPLITER).append(truncateDate(it.timestamps.calculated))
    row.append(SPLITER).append(truncateDate(it.timestamps.completed))

    //description
    row.append(SPLITER).append(String.valueOf(it.description).replace("\n", " ") )

    println(row)
}

def truncateDate(def input) {
    if (input) {
        return input.substring(0, 10)
    } else {
        return "";
    }
}