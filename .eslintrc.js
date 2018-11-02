module.exports = {
    extends: ['eslint:recommended', 'plugin:jest/recommended', 'prettier', 'plugin:react/recommended'],
    plugins: ['babel', 'jest', 'react'],
    parser: 'babel-eslint',
    env: {
        browser: true,
        commonjs: true,
        es6: true,
        node: true,
        jest: true
    },
    rules: {
        'comma-dangle': ['error', 'never'],
        indent: ['error', 4, { SwitchCase: 1 }],
        'linebreak-style': ['error', 'unix'],
        'no-unused-vars': ['warn'],
        'no-console': 2,
        'no-redeclare': 'warn',
        quotes: ['error', 'single', { avoidEscape: true }],
        semi: ['error', 'always'],
        'generator-star-spacing': ['error', { before: true, after: false }]
    },
    parserOptions: {
        sourceType: 'module',
        ecmaVersion: 6,
        ecmaFeatures: { jsx: true }
    },
    globals: { React: true },
    settings: {
        react: {
            createClass: 'createReactClass',
            pragma: 'React',
            version: 15.4,
            flowVersion: '0.53'
        },
        propWrapperFunctions: ['forbidExtraProps'],
        'import/resolver': {
            'babel-module': {
                extensions: ['.js', '.jsx']
            }
        }
    }
};
