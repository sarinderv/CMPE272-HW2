import React, {Component} from 'react';
import axios from 'axios';

export default class GetTweet extends Component {

  constructor(props) {
      super(props);
      this.onGetTweet = this.onGetTweet.bind(this);
      this.onSubmit = this.onSubmit.bind(this);
      this.state = {
          gettweet: '',
      }
  }

  onGetTweet(e) {
    this.setState({
        gettweet: e.target.value
    });
  }
  
  onSubmit(e) {
      e.preventDefault();
      let formData = new FormData(e.target);

      formData.append('id', this.state.gettweet);
      
      axios.get("http://132.145.52.5:8080/tweet", { params: { id: this.state.gettweet } }).then(response => {
        console.log(response.data.text);
        alert("You tweeted: "+response.data.text)
      });
  }
  
  render(){
    return(
      <div>
        <form onSubmit={this.onSubmit}>
            <div className="form-group">
                <label>Enter ID for Getting a tweet: </label>
                <input  type="text"
                        name = "gettweet"
                        className="form-control"
                        value={this.state.gettweet}
                        onChange={this.onGetTweet}
                        />
            </div>
            <div className="form-group">
                <input type="submit" value="Get Tweet" className="btn btn-primary" />
            </div>
        </form>
      </div>
    )
  }
}
