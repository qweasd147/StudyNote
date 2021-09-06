import { ArticleRepository } from './article.repository';
import { Injectable } from '@nestjs/common';
import { CreateArticleDto } from './dto/create-article.dto';
import { UpdateArticleDto } from './dto/update-article.dto';
import { InjectRepository } from '@nestjs/typeorm';
import { Article } from './entities/article.entity';
import { Repository } from 'typeorm';

@Injectable()
export class ArticleService {
  constructor(
    @InjectRepository(Article)
    private readonly articleRepository: Repository<Article>,
  ) {}

  create(createArticleDto: CreateArticleDto): Promise<Article> {
    const article = new Article();

    article.subject = createArticleDto.subject;
    article.content = createArticleDto.content;
    article.count = 0;

    return this.articleRepository.save(article);
  }

  async findAll(): Promise<[Article[], number]> {
    return await this.articleRepository.findAndCount();
  }

  findOne(id: number): Promise<Article> {
    return this.articleRepository.findOne(id);
  }

  async update(id: number, updateArticleDto: UpdateArticleDto) {
    const article = await this.articleRepository.findOne(id);

    article.subject = updateArticleDto.subject;
    article.content = updateArticleDto.content;
  }

  remove(id: number) {
    const deleteResult = this.articleRepository.delete(id);

    console.log('delete', deleteResult);
  }
}
