#!/usr/bin/env just --justfile

test *ARGS='--watch':
    lein kaocha {{ ARGS }}

repl:
    lein repl
