# Hexagonal Reference

## Overview

Reference project for **Hexagonal** architecture implementing the functionality defined for the BIAN domain [Payee Alias Directory Entry](https://bian.org/servicelandscape-14-0-0/object_13.html?object=157353):

- Create **Payee Alias Directory Entry**
- Retrieve **Payee Alias Directory Entry**
- Delete **Payee Alias Directory Entry**

## Diagrams

### Domain

```mermaid
flowchart LR
  subgraph Infrastructure ["Adapters (Infrastructure)"]
    direction TB
    A_IN[Inbound Adapters]
    A_OUT[Outbound Adapters]
    FW[[Frameworks / Libraries]]
  end

  subgraph Application ["Ports (Application)"]
    direction TB
    P_IN([Inbound Ports])
    CORE[Core]
    P_OUT([Outbound Ports])
    J_APP[[Java SDK]]
  end

  subgraph Domain ["Models and Enums (Domain)"]
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

### Application

```mermaid
flowchart LR
  subgraph Infrastructure ["Adapters (Infrastructure)"]
    direction TB
    A_IN[Inbound Adapters\nREST]
    A_OUT[Outbound Adapters\nDB]
    FW[[Frameworks / Libraries]]
  end

  subgraph Application ["Ports (Application)"]
    direction TB
    P_IN([Inbound Ports\nCreatePayeeAliasDirectoryEntryPortIn\nRetrievePayeeAliasDirectoryEntryPortIn\nDeletePayeeAliasDirectoryEntryPortIn])
    CORE[Core\nPayeeAliasDirectoryEntryCreator\nPayeeAliasDirectoryEntryRetriever\nPayeeAliasDirectoryEntryDeleter]
    P_OUT([Outbound Ports\nPayeeAliasDirectoryEntryPersistencePortOut])
    J_APP[[Java SDK]]
  end

  subgraph Domain ["Models and Enums (Domain)"]
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

#### Sequence - Create Payee Alias Directory Entry

```mermaid
sequenceDiagram
  participant Client as Client
  participant AdapterIn as Inbound Adapter<br/> REST
  participant PortIn as CreatePayeeAliasDirectoryEntryPortIn
  participant Core as PayeeAliasDirectoryEntryCreator
  participant PortOut as PayeeAliasDirectoryEntryPersistencePortOut
  participant AdapterOut as Outbound Adapter<br/>DB

  Client->>AdapterIn: HTTP Request
  AdapterIn->>PortIn: execute(input)
  PortIn-->>Core: execute(input)
  Core->>Core: validate(input)
  alt Validation Failed (e.g. empty fields)
    Core-->>AdapterIn: Throws PayeeAliasDirectoryEntryInputException
    AdapterIn-->>Client: Translates error to client (e.g. 400 Bad Request)
  else Validation Successful
    Core->>Core: Enriches domain object<br/>(reference, status, etc...)
    Core->>PortOut: create(model)
    alt DB Creation Failed
      PortOut->>AdapterOut: Creates record in DB
      AdapterOut-->>PortOut: Record creation fails
      PortOut-->>Core: Throws PayeeAliasDirectoryEntryPersistenceException
      Core-->>AdapterIn: Propagates PayeeAliasDirectoryEntryPersistenceException
      AdapterIn-->>Client: Translates error to client (e.g. 500 Internal Server Error)
    else DB Creation Successful
      PortOut->>AdapterOut: Creates record in DB
      AdapterOut-->>PortOut: Record created
      PortOut-->>Core: Completes model
      Core-->>AdapterIn: Maps output
      AdapterIn-->>Client: Translates success to client (e.g. 201 Created)
    end
  end
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

