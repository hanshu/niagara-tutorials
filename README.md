# niagara-tutorials
niagara development examples

## native libraries
- NiagaraLibraries
c++ native library project
- niagaraDll
how to use the above native library

## niagaraDatabase
illustrate these embedded databases usage:
1. H2
2. SQLite
3. Apache Derby

### database
sample databases are required to put under niagara station shares folder:
1. Derby
derby.system.home system property can be used to configure derby default working directory; and also derby library is put to [niagara home]/jre/lib/ext directory due to niagara security manager configuration.
2. SQLite
org.sqlite.lib.path system property configures the native SLite library path, org.sqlite.lib.name configures the native library name;

## niagaraMixin
>A MixIn is basically a mechanism to automatically add a dynamic slot to a specific type of component within a ComponentSpace.
