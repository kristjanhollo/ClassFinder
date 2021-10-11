# Swedbank homework

The goal of this homework is to implement class finder functionality
in a similar way to the Intellij IDEA Ctrl+N search.

Search tool should be run as follows:
```
./class-finder <filename> '<pattern>'
```

where `<filename>` refers to the file containing class names separated by line breaks, e.g.:

```
a.b.FooBarBaz
c.d.FooBar
```

Search pattern `<pattern>` must include class name camelcase upper case letters
in the right order and it may contain lower case letters to narrow down the search results,
for example `'FB'`, `'FoBa'` and `'FBar'` searches must all match
`a.b.FooBarBaz` and `c.d.FooBar` classes.

Upper case letters written in the wrong order will not find any results, for example
`'BF'` will not find `c.d.FooBar`.

If the search pattern consists of only lower case characters then the search becomes
case insensitive (`'fbb'` finds `FooBarBaz` but `'fBb'` will not).

If the search pattern ends with a space `' '` then the last word in the pattern must
also be the last word of the found class name (`'FBar '` finds `FooBar` and `FooBarzoo` but not `FooBarBaz`).

The search pattern may include wildcard characters `'*'` which match missing letters
(`'B*rBaz'` finds `FooBarBaz` but not `BrBaz`).

The found class names must be sorted in alphabetical order ignoring package names
(package names must still be included in the output).

Solution limitations:
- Java or Kotlin should be used.
- Use of other libraries (other than the language itself) is prohibited.

It is good enough if the solution implements the functionality described in this file.
