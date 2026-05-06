# Hexagonal Reference

## Overview

Reference project for **Hexagonal** architecture implementing the functionality defined for the BIAN domain [Payee Alias Directory Entry](https://bian.org/servicelandscape-14-0-0/object_13.html?object=157353):

- Create **Payee Alias Directory Entry**
- Retrieve **Payee Alias Directory Entry**
- Delete **Payee Alias Directory Entry**

## Diagrams

### Infrastructure

```mermaid
flowchart LR
  subgraph Infrastructure ["Infrastructure (Adapters)"]
    direction TB
    A_IN[Inbound Adapters\nCreatePayeeAliasDirectoryEntryHandler\nRetrievePayeeAliasDirectoryEntryHandler\nDeletePayeeAliasDirectoryEntryHandler]
    A_OUT[Outbound Adapters\nPostgresqlPayeeAliasDirectoryEntryPersistenceAdapter\nSlf4jLoggingAdapter\nJsoniterJsonTransformationAdapter]
    FW[[Frameworks / Libraries]]
  end

  subgraph Application ["Application (Ports)"]
    direction TB
    P_IN([Port In\nCreatePayeeAliasDirectoryEntryPortIn\nRetrievePayeeAliasDirectoryEntryPortIn\nDeletePayeeAliasDirectoryEntryPortIn])
    CORE[Core\nPayeeAliasDirectoryEntryCreator\nPayeeAliasDirectoryEntryRetriever\nPayeeAliasDirectoryEntryDeleter]
    P_OUT([Port Out\nPayeeAliasDirectoryEntryPersistencePortOut\nLoggingPortOut\nJsonTransformationPortOut])
    J_APP[[Java SDK]]
  end

  subgraph Domain ["Domain (Models and Enums)"]
    direction TB
    D_ENT[Models and Enums\nPayeeAliasDirectoryEntry\nPayeeAliasDirectoryEntryStatus]
    J_DOM[[Java SDK]]
  end

  A_IN -->|Uses| P_IN
  P_IN -->|Implemented by| CORE
  CORE -->|Uses| D_ENT
  CORE -->|Calls| P_OUT
  P_OUT -->|Implemented by| A_OUT

  classDef domain fill:#d4edda,stroke:#28a745,stroke-width:2px,color:#155724;
  classDef app fill:#cce5ff,stroke:#007bff,stroke-width:2px,color:#004085;
  classDef infra fill:#f8d7da,stroke:#dc3545,stroke-width:2px,color:#721c24;
  classDef techLabel fill:#fff,stroke-dasharray: 5 5;

  class Domain,J_DOM domain;
  class Application,P_IN,P_OUT,CORE,J_APP app;
  class Infrastructure,A_IN,A_OUT,FW infra;
  class J_APP,J_DOM,FW techLabel;
```

## Build

### Requirements

- [Docker](https://docs.docker.com/engine/install/)

### Run

```shell
docker run \
  --rm \
  -w $(pwd) \
  -v $(pwd):$(pwd) \
  -v ${HOME}/.m2:/root/.m2 \
  azul/zulu-openjdk-alpine:25.0.1 \
  ./mvnw -Djansi.force=true -ntp -U clean install
```

