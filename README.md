# foreman-chisel

## Status ##

![Build Status](https://github.com/foremanmining/foreman-chisel/actions/workflows/workflow.yml/badge.svg)

### Chisel ###

Chisel is an open-source Java application that will extract GPU-specific metrics
and expose them via an HTTP API. This application aims to provide a mechanism
for supplementing some of the lackluster miner APIs that often don't expose GPU
statistics, thus enabling Foreman to consistently report mining metrics across
all miners.

Chisel is currently being leveraged by the Foreman integration with nvOC,
HiveOS, and Linux. It will be expanded upon, with the intention of adding
support for Windows environments.

#### Endpoints ####

The following endpoints are exposed:

```
/stats/gpus
```

That endpoint returns a response formatted as follows (the statistics will be
different depending on your mining configuration):

```
[
  {
    "busId": 1,
    "clocks": {
      "core": 1999,
      "memory": 3504
    },
    "fan": 50,
    "id": 0,
    "name": "GeForce GTX 1050 Ti",
    "processes": [],
    "temp": 38
  },
  {
    "busId": 12,
    "clocks": {
      "core": 1999,
      "memory": 3504
    },
    "fan": 49,
    "id": 1,
    "name": "GeForce GTX 1050 Ti",
    "processes": [],
    "temp": 37
  },
  {
    "busId": 14,
    "clocks": {
      "core": 1999,
      "memory": 3504
    },
    "fan": 50,
    "id": 2,
    "name": "GeForce GTX 1050 Ti",
    "processes": [],
    "temp": 37
  }
]
```

## Requirements ##

- JDK version 8 (or higher)
- Apache Maven (only if building Chisel from sources)

## Building ##

To build chisel, from the top level of the repository:

```sh
$ mvn clean install
```

Upon a successful build, you should see something similar to the following:

```sh
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO]
[INFO] chisel-model ....................................... SUCCESS [  7.981 s]
[INFO] chisel-service ..................................... SUCCESS [  0.817 s]
[INFO] chisel-service-smi ................................. SUCCESS [  1.722 s]
[INFO] chisel-application ................................. SUCCESS [ 11.922 s]
[INFO] foreman-chisel ..................................... SUCCESS [  0.669 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 23.650 s
[INFO] Finished at: 2018-11-30T03:50:43Z
[INFO] Final Memory: 48M/467M
[INFO] ------------------------------------------------------------------------
```

The application distributions can be found in the `target/` folders. You'll only
need one - pick the extension you prefer.

```sh
$ ls -la **/target | grep -E "\.(zip|tar)"
-rwxrwxrwx 1 root root 15778531 Nov 29 22:50 chisel-application-1.0.0-SNAPSHOT-bin.tar.bz2
-rwxrwxrwx 1 root root 15836481 Nov 29 22:50 chisel-application-1.0.0-SNAPSHOT-bin.tar.gz
-rwxrwxrwx 1 root root 17808859 Nov 29 22:50 chisel-application-1.0.0-SNAPSHOT-bin.zip
$

```

## License ##

Copyright © 2018, [OBM LLC](https://obm.mn/). Released under
the [GPL-3.0 License](LICENSE).
