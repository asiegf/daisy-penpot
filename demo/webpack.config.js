const path = require('path');

module.exports = {
    mode: 'production',
    performance: {
        hints: false,
        maxEntrypointSize: 512000,
        maxAssetSize: 512000
    },

    // Entry point of your application
    entry: './src/index.js',

    // Output configuration for the bundle
    output: {
        path: path.resolve(__dirname, 'dist'), // output directory
        filename: 'bundle.js' // name of the generated bundle
    },

    // Configuration for the development server
    devServer: {
        static: path.join(__dirname, 'dist'), // The static files to serve
        compress: true, // Enable gzip compression
        port: 3000 // Port number for the web server
    },

    // Module rules and loaders
    module: {
        rules: [
            {
                test: /\.js$/, // Files to apply this loader to (all .js files)
                exclude: /node_modules/, // Exclude the node_modules directory
                use: {
                    loader: 'babel-loader', // Specifies to use Babel to transpile the files
                    options: {
                        presets: [
                            '@babel/preset-env', // Transpile ES6+ down to ES5
                            '@babel/preset-react' // Transpile JSX if you are using React (optional)
                        ]
                    }
                }
            }
        ]
    }
};
