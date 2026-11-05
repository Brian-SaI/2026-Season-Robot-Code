{
  "version": "2.0.0",
  "timestamp": "2026-11-05T01:14:16.509Z",
  "startPoint": {
    "x": 56.536662452591656,
    "y": 10.146649810366625,
    "locked": false,
    "headingDeg": 90
  },
  "lines": [
    {
      "id": "line-musifu5h-5nh5bf",
      "color": "#ADAAA5",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 9.073324905183313,
        "y": 8
      },
      "controlPoints": [
        {
          "x": 56.88621997471555,
          "y": 23.97092288242731
        },
        {
          "x": 44.54298356510746,
          "y": 9.481036662452594
        }
      ],
      "heading": {
        "type": "piecewise",
        "reverse": true,
        "degrees": -90,
        "startDeg": 0,
        "endDeg": 0,
        "piecewiseHeading": {
          "segments": [
            {
              "startProgress": 0,
              "endProgress": 0.5018495684340321,
              "interpolationType": "linear",
              "reversed": false,
              "continueFromPrevious": false,
              "parameters": {
                "startDeg": -90,
                "endDeg": 180
              }
            },
            {
              "startProgress": 0.5018495684340321,
              "endProgress": 1,
              "interpolationType": "constant",
              "reversed": false,
              "continueFromPrevious": false,
              "parameters": {
                "degrees": 180
              }
            }
          ]
        }
      }
    },
    {
      "id": "line-musig8zo-iy4rnh",
      "color": "#C57C68",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 58.58814747027302,
        "y": 8.931058455991849
      },
      "controlPoints": [
        {
          "x": 12.164348925410872,
          "y": 18.783185840707965
        },
        {
          "x": 27.90644753476612,
          "y": 27.190897597977244
        },
        {
          "x": 59.92730720606827,
          "y": 29.874209860935526
        }
      ],
      "heading": {
        "type": "linear",
        "reverse": false,
        "degrees": 0,
        "startDeg": 180,
        "endDeg": -90,
        "piecewiseHeading": {
          "segments": [
            {
              "startProgress": 0,
              "endProgress": 1,
              "interpolationType": "linear",
              "reversed": false,
              "parameters": {
                "startDeg": 0,
                "endDeg": 0
              }
            }
          ]
        }
      }
    },
    {
      "id": "line-musihlpe-8xhnzw",
      "color": "#BC875A",
      "name": "",
      "locked": false,
      "waitBeforeMs": 0,
      "waitAfterMs": 0,
      "waitBeforeName": "",
      "waitAfterName": "",
      "kind": "atomic",
      "endPoint": {
        "x": 13.764440438553065,
        "y": 94.35593977488556
      },
      "controlPoints": [
        {
          "x": 16.128705148205935,
          "y": 52.32917316692667
        }
      ],
      "heading": {
        "type": "constant",
        "reverse": true,
        "degrees": -90,
        "startDeg": 0,
        "endDeg": 0,
        "piecewiseHeading": {
          "segments": [
            {
              "startProgress": 0,
              "endProgress": 1,
              "interpolationType": "linear",
              "reversed": false,
              "parameters": {
                "startDeg": 0,
                "endDeg": 0
              }
            }
          ]
        }
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
      "lineId": "line-musifu5h-5nh5bf"
    },
    {
      "kind": "path",
      "lineId": "line-musig8zo-iy4rnh"
    },
    {
      "kind": "path",
      "lineId": "line-musihlpe-8xhnzw"
    }
  ],
  "fieldPoints": [],
  "robot": {
    "forwardVelocity": 75,
    "strafeVelocity": 65,
    "aVelocity": 3.141592653589793,
    "kFriction": 0.1,
    "rLength": 16,
    "rWidth": 16,
    "safetyMargin": 1,
    "maxAcceleration": 30,
    "maxDeceleration": 30
  },
  "activePaths": []
}