
This is alpha.
An initial setup for integrating Penpot with Daisy.


# Usage

Configure `.env.edn` with the file-id and penpot token access.

Generate a library
`shadow-cljs release module`

In you other project, use the `out/daisy.js` as a local dependency:
`npm install --save ../daisy.penpot/out/daisy.js`

You can checkout the `demo` folder for a usage example.


# Development

Run server:
`npx shadow-cljs server`

Run watcher:
`shadow-cljs wath dev`

In emacs:
`M-x cider-connect-clj&cljs`
