import React from 'react';
import {Link} from 'react-router-dom';
import './Navbar.css';
import img1 from '../../img/profile-picture-default.jpeg';
import SearchBar from '../SearchBar/SearchBar';
import { useDispatch, useSelector } from 'react-redux';
import {logout} from '../../actions/userActions';

export const Navbar: React.FC<any> = () => {

    const state = useSelector<any, any>((state) => state);

    const dispatch = useDispatch();

    const onLogout = ()=> {
        dispatch(
            logout()
        )
    }

    return (
        <div>
            <nav className='navbar'>
                <Link className='navbar-logo' to={`/user/${state.user.user.username}`} ><img className="profile-pic-icon" src={img1} alt="profile-pic" /></Link>
                <ul className="nav-menu">
                    <li className='nav-item'><Link to={`/user/${state.user.user.username}`} className='nav-links'>Profile</Link></li>
                    <li className='nav-item'><Link to="/home" className='nav-links'>Feed</Link></li>
                    <li className='nav-item' ><SearchBar /></li>
                    <li className='logout'><Link to="/"><button className="logout-button" onClick={onLogout}>Logout</button></Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar;
