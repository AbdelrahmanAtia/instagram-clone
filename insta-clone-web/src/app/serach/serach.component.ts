import { Component } from '@angular/core';

@Component({
  selector: 'insta-serach',
  templateUrl: './serach.component.html',
  styleUrls: ['./serach.component.css']
})
export class SerachComponent {
  searchTerm: string = '';
  items: string[] = ['Apple', 'Banana', 'Orange', 'Pineapple', 'Mango'];
  filteredItems: string[] = this.items;

  onSearch() {
    this.filteredItems = this.items.filter((item) =>
      item.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
