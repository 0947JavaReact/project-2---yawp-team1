import { CallToActionSharp } from '@material-ui/icons';
import { isTemplateSpan } from 'typescript';
import { ADD_YAWP, CLEAR_YAWPS, FETCH_USER_YAWPS, FETCH_FOLLOWING_YAWPS, LIKE_YAWP } from '../actions/types'
import update from 'react-addons-update';

export interface Yawp {
    id: number,
    username: string,
    content: string,
    likes: number,
    profilePic: string
}

const initialState = {
    items: [],
    fetched: false
}
export type Action = { type: string; payload: any };
export const yawpReducer = (state = initialState, action: Action) => {
    switch (action.type) {
        case ADD_YAWP:
            return {
                ...state,
                items: [action.payload, ...state.items]
            };

        case CLEAR_YAWPS:
            return {
                ...state,
                items: action.payload
            }

        case FETCH_FOLLOWING_YAWPS:
            return {
                ...state,
                items: action.payload,
                fetched: true
            };
        case FETCH_USER_YAWPS:
            return {
                ...state,
                items: action.payload,
                fetched: true
            }
        case LIKE_YAWP:


            let likes = action.payload.yawp.likes

            return {
                ...state,
                items: state.items.map(
                    (yawp:any, i) => yawp.yawpId === action.payload.yawp.yawpId ? {...yawp, likes: likes}: yawp
                )
            }

        default:
            return state;
    }
}
