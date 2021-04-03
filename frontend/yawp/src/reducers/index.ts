import { combineReducers } from 'redux'
import { userReducer } from './userReducer'
import { yawpReducer } from './yawpReducer'

export default combineReducers({
    user: userReducer,
    yawp: yawpReducer
})