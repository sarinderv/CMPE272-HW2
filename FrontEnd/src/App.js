import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

import CreateTweet from './components/CreateTweet';
import GetTweet from "./components/GetTweet";
import DelTweet from './components/DelTweet';

class App extends Component {
  render() {
    return (
      <Router>
        <div className="container">          
          <nav className="navbar navbar-expand-lg ">
            <div className="collpase nav-collapse">
              <ul className="navbar-nav mr-auto">
                <li className="navbar-item">
                  <Link to="/" className="nav-link">View</Link>
                </li>
                <li className="navbar-item">
                  <Link to="/create" className="nav-link">Create</Link>
                </li>
                <li className="navbar-item">
                  <Link to="/delete" className="nav-link">Delete</Link>
                </li>
              </ul>
            </div>
          </nav>
          <Route path="/" exact component={GetTweet} />
          <Route path="/create" component={CreateTweet} />
          <Route path="/delete" component={DelTweet} />
        </div>
      </Router>
    );
  }
}

export default App;