
config.resolve.fallback = {
    fs: false,
    path: false,
    crypto: false,
};

const CopyWebpackPlugin = require('copy-webpack-plugin');
config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            {
                from: '../../node_modules/sql.js/dist/sql-wasm.wasm',
                to: '../../../webApp/build/distributions'
            }
        ]
    })
);