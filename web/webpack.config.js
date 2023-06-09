const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    landingPage: path.resolve(__dirname, 'src', 'pages', 'landingPage.js'),
    viewProfile: path.resolve(__dirname, 'src', 'pages', 'viewProfile.js'),
    createProfile: path.resolve(__dirname, 'src', 'pages', 'createProfile.js'),
    viewAllExpenses: path.resolve(__dirname,'src', 'pages', 'viewAllExpenses.js'),
    createExpense: path.resolve(__dirname, 'src', 'pages', 'createExpense.js'),
    updateExpense: path.resolve(__dirname, 'src', 'pages', 'updateExpense.js'),
    viewAllIncome: path.resolve(__dirname,'src', 'pages', 'viewAllIncome.js'),
    createIncome: path.resolve(__dirname, 'src', 'pages', 'createIncome.js'),
    updateIncome: path.resolve(__dirname, 'src', 'pages', 'updateIncome.js'),
    viewRunningBalance: path.resolve(__dirname, 'src', 'pages', 'viewRunningBalance.js'),

  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}