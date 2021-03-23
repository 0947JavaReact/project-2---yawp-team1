import {applyMiddleware, createStore} from 'redux'
import thunk from 'redux-thunk'
import reducer from './reducers'

const initialState = {}
//Initial state
/*
    User
        username
        id

    Posts
        Array[...post]

    Post
        
*/
const middleWare =[thunk];
export const store = createStore(reducer,initialState,applyMiddleware(...middleWare));