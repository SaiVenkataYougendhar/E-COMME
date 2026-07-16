import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default {
  mode: process.env.NODE_ENV || 'development',
  entry: './src/index.ts',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
  devServer: {
    port: 3000,
    hot: true,
    open: false,
    historyApiFallback: {
      index: '/swiftcart-html/index.html'
    },
    static: [
      {
        directory: path.join(__dirname, '../swiftcart-html'),
        publicPath: '/swiftcart-html',
      },
      {
        directory: path.join(__dirname, '../swiftcart-html'),
        publicPath: '/',
      }
    ],
  },
  devtool: process.env.NODE_ENV === 'production' ? false : 'source-map',
};
