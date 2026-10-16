{
  "startPoint": {
    "x": 59.78730272596842,
    "y": 133.2145355093257,
    "locked": false,
    "headingDeg": 90
  },
  "lines": [
    {
      "id": "line-mva6bthd-693uvj",
      "color": "#ffc516",
      "name": "Path 1",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 2500,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 59.85724533715925,
        "y": 105.43041606886659
      },
      "controlPoints": [],
      "heading": {
        "type": "linear",
        "startDeg": 90,
        "endDeg": 90
      }
    },
    {
      "id": "line-mva6fi9u-iqf6x0",
      "color": "#77D7B6",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 47.46556671449068,
        "y": 135.58823529411765
      },
      "controlPoints": [],
      "heading": {
        "type": "tangential",
        "reverse": false
      }
    },
    {
      "id": "line-mva6gm59-7lbzhs",
      "color": "#55B76D",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 59.32281205164994,
        "y": 116.18220946915353
      },
      "controlPoints": [],
      "heading": {
        "type": "tangential",
        "reverse": true
      }
    },
    {
      "id": "line-mva6gzpr-hdcbkr",
      "color": "#BC56D6",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 59.70588235294117,
        "y": 105.6929698708752
      },
      "controlPoints": [],
      "heading": {
        "type": "tangential",
        "reverse": true
      }
    },
    {
      "id": "line-mva6h3in-yu5l7s",
      "color": "#99B965",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 7.171449067431853,
        "y": 115.41535150645623
      },
      "controlPoints": [],
      "heading": {
        "type": "tangential",
        "reverse": true
      }
    }
  ],
  "shapes": [
    {
      "id": "triangle-1",
      "name": "Red Goal",
      "vertices": [
        {
          "x": 141.5,
          "y": 70
        },
        {
          "x": 141.5,
          "y": 141.5
        },
        {
          "x": 118.3,
          "y": 141.5
        },
        {
          "x": 135.5,
          "y": 118
        },
        {
          "x": 136.3,
          "y": 70.2
        }
      ],
      "color": "#dc2626",
      "fillColor": "#ff6b6b"
    },
    {
      "id": "triangle-2",
      "name": "Blue Goal",
      "vertices": [
        {
          "x": 6.2,
          "y": 116.9
        },
        {
          "x": 25,
          "y": 141.5
        },
        {
          "x": 0,
          "y": 141.5
        },
        {
          "x": 0,
          "y": 70
        },
        {
          "x": 6,
          "y": 70
        }
      ],
      "color": "#2563eb",
      "fillColor": "#60a5fa"
    }
  ],
  "sequence": [
    {
      "kind": "path",
      "lineId": "line-mva6bthd-693uvj"
    },
    {
      "kind": "path",
      "lineId": "line-mva6fi9u-iqf6x0"
    },
    {
      "kind": "path",
      "lineId": "line-mva6gm59-7lbzhs"
    },
    {
      "kind": "path",
      "lineId": "line-mva6gzpr-hdcbkr"
    },
    {
      "kind": "path",
      "lineId": "line-mva6h3in-yu5l7s"
    }
  ],
  "fieldPoints": [],
  "activePaths": [],
  "settings": {
    "xVelocity": 75,
    "yVelocity": 65,
    "aVelocity": 3.141592653589793,
    "kFriction": 0.1,
    "rWidth": 16,
    "rHeight": 16,
    "safetyMargin": 1,
    "maxVelocity": 40,
    "maxAcceleration": 30,
    "maxDeceleration": 30,
    "fieldMap": "biobuzz.webp",
    "robotImage": "/robot.png",
    "showGhostPaths": false,
    "showOnionLayers": false,
    "onionLayerSpacing": 3,
    "onionColor": "#dc2626",
    "onionNextPointOnly": false,
    "showHeadingArrow": false,
    "showCurrentTValue": false,
    "leftPanelWidth": 370,
    "rightPanelWidth": 620,
    "headingArrowLength": 50,
    "headingArrowColor": "#ffffff",
    "headingArrowThickness": 2,
    "pathOpacity": 1,
    "leftPanelMinWidth": 0,
    "rightPanelMinWidth": 0,
    "penToolMaxPaths": 8,
    "curveThroughMaxPoints": 4,
    "experimentalFeatures": {
      "optimize": false,
      "curveThrough": false
    }
  },
  "version": "1.5.0",
  "timestamp": "2026-10-16T00:08:06.219Z"
}