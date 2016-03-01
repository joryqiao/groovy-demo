package com.groovy.demo.client

/**
 * Created by qiaorongrong on 3/1/16.
 */


File file = new File('../jsonparse/json1.json')
def jsonTxt = file.getText('UTF-8')
print(jsonTxt)

//def resource = this.getClass().getResource('json1.json')
//print(resource)
