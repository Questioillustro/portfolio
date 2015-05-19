var webpack = require('webpack');
var path = require("path");

var root = __dirname;

module.exports = {
    entry: {
        attendee: './src/app.entries.attendee.jsx',
        facilitator: './src/app.entries.facilitator.jsx'
    },
    output: {
        path: path.join(__dirname, 'public/js'),
        filename: '[name].js', // Template based on keys in entry above
        //sourceMapFilename: '[file].map'
    },
    module: {
        loaders: [
            //{ test: /\.jpg$/, loader: 'file?name=map.jpg!../public/img/map.jpg' },
            { test: /\.jsx$/, loader: 'jsx-loader' },
            { test: /\.css$/, loader: "style!css" },
            // { test: /\.styl$/, loader: 'style-loader!css-loader!stylus-loader' },
            // { test: /\.styl$/, loader: 'style-loader!css-loader!stylus-loader?paths=node_modules/bootstrap-styl/' },
            { test: /\.styl$/, loader: 'style-loader!css-loader!stylus-loader?paths=node_modules/my-bootstrap-styl/' },
            //{ test: /\.jpg$/, loader: 'file-loader?name=map.jpg' },
            // { test: /\.jpg$/, loader: 'file-loader' },
            { test: /\.png$/, loader: 'file-loader' },


            // bootstrap-sass-loader:
            // **IMPORTANT** This is needed so that each bootstrap js file required by
            // bootstrap-webpack has access to the jQuery object
            { test: /bootstrap\/js\//, loader: 'imports?jQuery=jquery' },

            // Needed for the css-loader when [bootstrap-webpack](https://github.com/bline/bootstrap-webpack)
            // loads bootstrap's css.
            { test: /\.woff(\?v=\d+\.\d+\.\d+)?$/,   loader: "url?limit=10000&minetype=application/font-woff" },
            { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,    loader: "url?limit=10000&minetype=application/octet-stream" },
            { test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,    loader: "file" },
            { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,    loader: "url?limit=10000&minetype=image/svg+xml" }

            // the url-loader uses DataUrls.
            // the file-loader emits files.
            // { test: /\.woff$/,   loader: "url-loader?limit=10000&minetype=application/font-woff" },
            // { test: /\.ttf$/,    loader: "file-loader" },
            // { test: /\.eot$/,    loader: "file-loader" },
            // { test: /\.svg$/,    loader: "file-loader" }
        ]
    },
    resolve: {
        extensions: ['', '.js', '.jsx','.styl'],
        //extensions: ['', '.js', '.jsx'],
        alias: {
            alias_simple_sidebar: root + '/bower_components/simple-sidebar/css/simple-sidebar.css',
            alias_socket_io: root + '/node_modules/socket.io/node_modules/socket.io-client/index.js',
            alias_react_autocomplete: root + '/bower_components/ron-react-autocomplete/lib/index.js'
        }
    },
    devtool: 'sourcemap',
    plugins: [
        new webpack.ProvidePlugin({
            $: path.join(root, "/bower_components/jquery/dist/jquery.js"),
            jQuery: path.join(root, "/bower_components/jquery/dist/jquery.js")
        }) 
    ]
    //provide: { $: path.join(root, "/bower_components/jquery/dist/jquery.js"), jQuery: path.join(root, "/bower_components/jquery/dist/jquery.js") }
};
