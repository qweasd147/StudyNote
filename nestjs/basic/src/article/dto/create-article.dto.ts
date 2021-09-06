import { Article } from '../entities/article.entity';

export class CreateArticleDto {
  readonly subject: string;
  readonly content: string;

  toEntity(): Article {
    const article = new Article();

    article.subject = this.subject;
    article.content = this.content;
    article.count = 0;

    return article;
  }
}
