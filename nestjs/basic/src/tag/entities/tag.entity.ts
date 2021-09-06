import {
  Entity,
  Column,
  ManyToOne,
  PrimaryGeneratedColumn,
  JoinColumn,
} from 'typeorm';
import { Article } from 'src/article/entities/article.entity';

@Entity()
export class Tag {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @ManyToOne((type) => Article, (article: Article) => article.tags)
  @JoinColumn({ name: 'article_id' })
  article: Article;
}
