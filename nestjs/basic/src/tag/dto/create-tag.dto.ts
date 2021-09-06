import { Tag } from '../entities/tag.entity';

//import { IsNotEmpty } from 'class-validator';

export class CreateTagDto {
  readonly articleId: number;
  readonly name: string;

  toEntity(articleId: number): Tag {
    const tag = new Tag();

    tag.name = this.name;
    tag.article.id = this.articleId;

    return tag;
  }
}
