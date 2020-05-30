import 'dart:developer';
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

void main() => runApp(_widgetForRoute(window.defaultRouteName));

Widget _widgetForRoute(String url) {
  print("_widgetForRoute:$url");

  Fluttertoast.showToast(
      msg: "This is Center Short Toast",
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.CENTER,
      timeInSecForIosWeb: 1,
      backgroundColor: Colors.red,
      textColor: Colors.white,
      fontSize: 16.0);

  log("route = $url");
  debugPrint("route = $url");
  String route = url;
  switch (route) {
    case 'route1':
      return MaterialApp(
        home: Scaffold(
          appBar: AppBar(
            title: Text('Flutter页面'),
          ),
          body: Center(
            child: Text('页面名字: $route',
                style: TextStyle(color: Colors.red),
                textDirection: TextDirection.ltr),
          ),
        ),
      );
    default:
      return Center(
        child: Text('Unknown route: $route',
            style: TextStyle(color: Colors.red),
            textDirection: TextDirection.ltr),
      );
  }
}
